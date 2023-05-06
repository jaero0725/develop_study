# java remind

## 1. Collection Framework and interface

````
List : 순서가 있는데이터 집합, 중복허용
Set : 순서를 유지 하지 않는 데이터의 집합, 중복을 허용하지 않는다.
Map : 키와 값 쌍으로 이루어진 데이터 집합, 순서는 유지되지 않으며, 키 중복을 허용하지 않고 값 중복을 허용한다.

List
Vector 
- Stack
ArrayList
LinkedList

Set
HashSet 
SortedSet 
- TreeSet

Map
HashTable
HashMap
-LinkedHashMap
SortedMap
-TreeMap 


````

### Q. ArrayList vs LinkedList
````
ArrayList ::  읽기 :빠르다 / 추가삭제 :느리다 /순차적인 추가삭제는 더 빠름, 비효율적인 메모리 사용
LinkedList ::  읽기 :느리다 /추가삭제 :빠르다 / 데이터가 많을 수록 접근성이 떨어짐
다루고자 하는 데이터의 개수가 변하지 않는 경우라면, ArrayList가 최상의 선택이 되겠지만,
데이터 개수의 변경이 잦다면 LinkedList를 사용하는 것이 더 나은 선택이 될 것이다. 
````

