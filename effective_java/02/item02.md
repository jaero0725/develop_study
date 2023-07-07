# 아이템2. 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩터리와 생성자에는 똑같은 제약이 하나 있다. 선택적 매개변수가 많을 때 적절히 대응하기 어렵다는 점이다.
프로그래머들은 이럴 때 점층적 생성자 패턴? 을 즐겨 사용했다.
그냥 하나씩 매개변수 늘려가는 방식.. (별로임..)

```java
public class NutritionFacts {	
	private final int servingSize;  // 필수
	private final int servings;     // 필수
	private final int calories;     // 선택
	private final int fat;          // 선택
	private final int sodium;       // 선택
	private final int carbohydrate; // 선택
	
	public NutritionFacts(int servingSize, int servings) {
		this(servingSize, servings, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories) {
		this(servingSize, servings, calories, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat) {
		this(servingSize, servings, calories, fat, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
		this(servingSize, servings, calories, fat, sodium, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
}

```
=> 읽기 힘들다. 순서 바뀌면 뭔지 못알아 먹고 런타임 오류남

- 아래는 자바 빈즈 패턴이다. 매개변수 없는 생성자 객체로 만들고,  Setter 메서드를 호출하여 매개변수 값을 설정함.
  
```java

public class NutritionFacts {
    private int servingSize = -1;
    private int servings = -1;
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts() { }

    public void setServingSize(int val) { ... }
    public void setServings(int servings) { ... }
    public void setCalories(int calories) { ... }
    public void setFat(int fat) { ... }
    public void setSodium(int sodium) { ... }
    public void setCarbohydrate(int carbohydrate) { ... }

}

```

- 좀 읽기 쉬운 코드가 되었다.
- (심각한 단점!) 그러나, 자바 빈즈 패턴에서 객체 하나 만들려면 메서드를 여러 개 호출해야 하고 객체가 완전히 생성되기전까지 일관성이 무너질 수 있음.
  클래스를 불면으로 만들 수 없고, 스레드 안전성을 지키려면 추가 작업이 필요함
- 단점을 완화하고자 얼리고, 얼리기전에 사용할 수 없도록 한다? -> 컴파일러가 보증할 방법이 없어 런타임 오류에 취약함.

=> 점층적 생성자 패턴의 안정성과 자바 빈즈 패턴의 가독성을 겸비한 "빌더 패턴"을 사용한다면?

- 클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(혹은 정적 팩터리)를 호출해 빌더 객체를 얻는다.
- 빌더는 생성할 클래스 안에 정적 멤버 클래스로 만들어두는게 보통이다.
``` java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화한다. 
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) { calories = val; return this }
        public Builder fat(int val) { fat = val; return this }
        public Builder sodium(int val) { sodium = val; return this }
        public Builder carbohydrate(int val) { carbohydrate = val; return this }

        private NutritionFacts(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }
    }
}


// 이런식으로 씀 ,
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                .calories(100)
                .fat(35)
                .sodium(40)
                .carbohydrate(1)
                .build();


```

- 이 클라이언트 코드는 쓰기 쉽고, 무엇보다도 읽기 쉽다.
- 빌더 패턴은 상당히 유연하다. 빌더 하나로 여러 객체를 순회하면서 만들 수 있고, 빌더에 넘기는 매개변수에 따라 다른 객체를 만들 수도 있다.
- 빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기가 좋다. <a href="https://debaeloper.tistory.com/35"> 참조 </a>

#### 단점?
```
객체만들려면 앞서 빌더부터 만들어야되어서, 성능에 민감한 상황에서는 문제가 될수 있다. 
매개변수 4개 이상일 경우 값어치를 함. 
API 는 매개변수가 점점 많아지니깐 미리 빌더패턴으로 만들어 놓는게 좋을 수있다.
```
