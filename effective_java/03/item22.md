# 아이템 22. 인터페이스는 타입을 정의하는 용도로만 사용하라

- 상수 인터페이스
- 상수 인터페이스 : 메서드 없이, 상수를 뜻하는 static final 필드로만 가득 찬 인터페이스를 말한다. 
- 이 상수들을 사용하는 클래스에서는 정규화된 이름을 쓰는 걸 피하고자 그 인터페이스를 구현하곤 한다. 

### ex 1) 상수 인터페이스 안티패턴 - 사용금지

``` java 
public interface PhysicalConstants {
    // 아보가드로 수 (1/몰)
    static final double AVOGADROS_NUMBER   = 6.022_140_857e23;

    // 볼츠만 상수 (J/K)
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;

    // 전자 질량 (kg)
    static final double ELECTRON_MASS      = 9.109_383_56e-31;
}
```

- 상수 인터페이스 안티패턴은 인터페이스를 잘못 사용한 예다.
- 상수 인터페이스를 구현하는 것은 이 내부 구현을 클래스의 API로 노출하는 행위
- 클래스가 어떤 상수 인터페이스를 사용하든 사용자에게는 아무런 의미가 없으며, 사용자에게 혼란을 줄수도 있고 클라이언트 코드가 내부 구현에 해당하는 이 상수들에 종속되게 함
- final이 아닌 클래스가 상수 인터페이스를 구현한다면 모든 하위 클래스의 이름 공간이 그 인터페이스가 정의한 상수들로 오염된다.
- java.io.ObjectStreamConstants 등, 자바 플랫폼 라이브러리에도 상수 인터페이스가 몇 개 있으나 잘못된 예이다.
 
<hr>

- 상수를 공개하는 다른 대안
- 특정 클래스나 인터페이스와 강하게 연관된 상수라면 그 클래스나 인터페이스 자체에 추가
- ex) Integer, Double에 선언된 MAX_VALUE, MIN_VALUE
- 열거 타입으로 나타내기 적합한 상수라면 열거 타입으로 만들어 공개
- 적합하지 않다면, 인스턴스화 할 수 없는 유틸리티 클래스에 담아 공개
 

### ex 2) 상수 유틸리티 클래스

``` java

public class PhysicalConstants {
    private PhysicalConstants() { }  // private 생성자로 인스턴스화 방지

    // 아보가드로 수 (1/몰)
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    // 볼츠만 상수 (J/K)
    public static final double BOLTZMANN_CONST  = 1.380_648_52e-23;
    // 전자 질량 (kg)
    public static final double ELECTRON_MASS    = 9.109_383_56e-31;
}
```

- 사용 방법 : PhysicalConstants.AVOGADROS_NUMBER
- 유틸리티 클래스의 상수를 빈번히 사용한다면 정적 임포트하여 클래스 이름 생략 가능
 

### ex 3) 정적 임포트를 사용해 상수 이름만으로 사용

``` java

import static effectivejava.chapter4.item22.constantutilityclass.PhysicalConstants.*;

public class Test {
    double atoms(double mols){
        return AVOGADROS_NUMBER * mols;
    }
    ...
    // PhysicalConstants 를 빈번히 사용한다면 정적 임포트가 값어치를 한다.
}
```
