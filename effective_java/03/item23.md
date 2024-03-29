# 아이템 23. 태그 달린 클래스보다는 클래스 계층 구조를 활용하라

- 태그 달린 클래스
- 두 가지 이상의 의미를 표현할 수 있으며, 그 중 현재 표현하는 의미를 태그 값으로 알려주는 클래스가 있다.

### ex 1) 태그 달린 클래스(원과 사각형을 표현)
 
```java
class Figure {
    enum Shape { RECTANGLE, CIRCLE };

    final Shape shape; // 태그 필드 - 현재 모양을 나타낸다.

    // 다음 필드들은 모양이 사각형(RECTANGLE)일 때만 쓰인다.
    double length;
    double width;

    // 다음 필드느 모양이 원(CIRCLE)일 때만 쓰인다.
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```

- 열거 타입 선언, 태그 필드, switch문 등 쓸데 없는 코드가 많다.
- 여러 구현이 한 클래스에 혼합되어 있어 가독성이 안좋다.
- 다른 의미를 위한 코드도 함께 내장되어 있어 메모리를 많이 사용한다.
- 필드를 final로 선언하려면 해당 의미에서 쓰이지 않는 필드들까지 생성자에서 초기화해야한다.
- 즉, 태그 달린 클래스는 장황하고 오류를 내기 쉬워 비효율적이다.
 
 <hr>

- 태그 달린 클래스를 클래스 계층 구조로 변경하는 방법
- 먼저 계층구조의 루트(root)가 될 추상 클래스를 정의하고, 태그 값에 따라 동작이 달라지는 메서드들을 루트 클래스의 추상 메서드로 선언한다.
- ex) ex1 코드의 area()
- 태그 값에 상관없이 동작이 일정한 메서드들을 루트 클래스에 일반 메서드로 추가한다.
- 모든 하위 클래스에서 공통으로 사용하는 데이터 필드들도 전부 루트 클래스로 올린다.
- 루트 클래스를 확장한 구체 클래스를 의미별로 하나씩 정의
 

### ex 2) 위의 ex1 코드를 클래스 계층구조로 변환

``` java
abstract class Figure {
    abstract double area();
}

class Circle extends Figure {
    final double radius;
    Circle(double radius) { this.radius = radius; }
    @Override double area() { return Math.PI * (radius * radius); }
}

class Rectangle extends Figure {
    final double length;
    final double width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    @Override
    double area() { return length * width; }
}
```

- 쓸데없는 코드가 사라져 간결하고 명확해짐
- 각 의미를 독립된 클래스에 담아 관련없던 데이터 필드를 모두 제거
- 살아남은 필드들은 final 필드임
- 각 클래스의 생성자가 모든 필드를 남김없이 초기화하고 추상 메서드를 잘 구현했는 지 컴파일러가 확인해줌
- 타입 사이의 자연스러운 계층 관계를 반영할 수 있어 유연성은 물론 컴파일타임 타입 검사 능력을 높여줌
 

### ex 3) 클래스 계층구조에서 정사각형을 사각형을 이용해서 정의

``` java 
class Square extends Rectangle {
    Square(double side) {
        super(side, side);
    }
}
```
 
<hr>

#### 정리
- 새로운 클래스를 작성하는 데 태그 필드가 등장한다면 태그를 없애고 계층구조를 사용하는 것을 고려하자.
- 기존 클래스가 태그 필드를 사용한다면 계층구조로 리팩터링을 고려하자
