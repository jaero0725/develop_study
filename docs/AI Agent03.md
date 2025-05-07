# LangGraph
- 코드로 만들기 때문에 세부적인 부분 처리가 좋음
- 랭체인 라이브러

## LangGraph의 장점
- Node(노드), Edge(엣지), State(상태관리)를 통해 LLM 워크플로우에 순환(Cycle)을 추가하여 손쉽게 흐름을 제어.
- 한쪽으로 만 진행되지 않고 여러번 반복하게 할 수 있다. 
- RAG 파이프라인의 **세부 단계별 흐름제어**가 가능
- **Conditional Edge:** 조건부 흐름 제어 / n8n에서 if라고 보면된다. 
- **Checkpointer :** 과거 실행 과정에 대한 수정 & 리플레이
# Langgraph 구성

## 상태(State)

노드와 노드 간의 정보를 전달할 때 상태(State) 객체에 담아 전달합니다.

```python
# State 정의
class GraphState(TypedDict):
    context: Annotated[List[Document], add_messages]
    answer: Annotated[List[Document], add_messages]
    question: Annotated[str, "user question"]
    sql_query: Annotated[str, "sql query"]
    binary_score: Annotated[str, "binary score yes or no"]
```

- TypedDict : 일반 파이썬 dict에 타입힌팅을 추가한 개념입니다.
    1. `dict`와 `TypedDict`의 주요 차이점
        - **타입 검사**
            - `dict`: 런타임에 타입 검사를 하지 않습니다.
            - `TypedDict`: 정적 타입 검사를 제공합니다. 즉, 코드 작성 시 IDE나 타입 체커가 오류를 미리 잡아낼 수 있습니다.
        - **키와 값의 타입**
            - `dict`: 키와 값의 타입을 일반적으로 지정합니다 (예: Dict[str, str]).
            - `TypedDict`: 각 키에 대해 구체적인 타입을 지정할 수 있습니다.
        - **유연성**
            - `dict`: 런타임에 키를 추가하거나 제거할 수 있습니다.
            - `TypedDict`: 정의된 구조를 따라야 합니다. 추가적인 키는 타입 오류를 발생시킵니다.
    2. `TypedDict`가 `dict` 대신 사용되는 이유
        - **타입 안정성**
            - `TypedDict`는 더 엄격한 타입 검사를 제공하여 잠재적인 버그를 미리 방지할 수 있습니다.
        - **코드 가독성**
            - `TypedDict`를 사용하면 딕셔너리의 구조를 명확하게 정의할 수 있어 코드의 가독성이 향상됩니다.
        - **IDE 지원**
            - `TypedDict`를 사용하면 IDE에서 자동 완성 및 타입 힌트를 더 정확하게 제공받을 수 있습니다.
        - **문서화**
            - `TypedDict`는 코드 자체가 문서의 역할을 하여 딕셔너리의 구조를 명확히 보여줍니다.

## 노드(Node)

- 함수로 정의합니다.
- 입력 인자 : 상태(State) 객체
    
    ```python
    # 문서 검색 노드
    def retrieve_document(state: GraphState) -> GraphState:
        # 질문을 상태에서 가져옵니다.
        latest_question = state["question"]
    
        # 문서에서 검색하여 관련성 있는 문서를 찾습니다.
        retrieved_docs = pdf_retriever.invoke(latest_question)
    
        # 검색된 문서를 형식화합니다.(프롬프트 입력으로 넣어주기 위함)
        retrieved_docs = format_docs(retrieved_docs)
    
        # 검색된 문서를 context 키에 저장합니다.
        return {"context": retrieved_docs}
    ```
    
- 반환(return)
    - 주로 상태(state) 객체
    - Conditional Edge의 경우 다를 수 있음

## Edge

- 노드에서 노드 간의 연결
- 연결 방법 : add_edge(”출발 노드 이름”, “도착 노드 이름”)
    
    ```python
    # 노드 정의
    workflow.add_node("retrieve", retrieve_document)
    workflow.add_node("llm_answer", llm_answer)
    ```
    

## 조건부 엣지(Conditional Edge)

- 조건부 엣지는 노드의 분기점을 만들어줍니다.
- 추가 방법 : add_conditional_edges(”출발 노드 이름”, “조건부 판단 함수”, 판단 결과 dict)

```python
workflow.add_conditional_edges(
    "validate_sql_query",
    decision,
    {
        "QUERY ERROR": "rewrite_query",
        "UNKNOWN MEANING": "rewrite_question",
        "PASS": "GPT 요청",
    },
)
```

## 그래프 실행

- RunnableConfig
    - recursion_limit : 최대 노드 실행 개수를 지정합니다.(무한 루프 방지)
    - thread_id : 그래프 실행 아이디를 기록하고, 나중에 추적하기 위해 사용합니다.
