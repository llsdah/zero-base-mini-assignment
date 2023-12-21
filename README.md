# zero-base-mini-assignment
제로베이스 미니 기술 구현 
[ 회원 초대 링크 생성 기능 ]

✅ 프로젝트 구성 방법 및 관련된 시스템 아키텍처 설계 방법이 적절한가?
✅ 작성한 애플리케이션 코드의 가독성이 좋고 의도가 명확한가? (e.g. 불필요한(사용되지) 않는 코드의 존재 여부, 일정한 코드 컨벤션 등)
✅ 작성한 테스트 코드는 적절한 범위의 테스트를 수행하고 있는가? (e.g. 유닛/통합 테스트 등)
✅ Spring Boot의 기능을 적절히 사용하고 있는가?
✅ 예외 처리(Exception Handling)은 적절히 수행하고 있는가?

+ 추가요건
✅ 프로젝트 구성 추가 요건
- 멀티 모듈 구성 및 모듈간 의존성 제약

✅ Back-end 추가 요건
1. 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 둔 구현
2. 다수의 서버, 인스턴스에서 동작할 수 있음을 염두에 둔 구현
3. 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 구현
4. 생성 이후 일정 시간동안 만료되지 않은 초대 링크의 자동 만료를 염두에 둔 구현

=> 코드작업방법
1. Member @Cacheable 및 @Cashe 전략 사용, MemberModel 클래스의 index 어노테이션 사용
2. 다수 서버일 경우 db 동시 수정이 발생 가능해 @Transaction 추가 
3. LinkModel의 경우 데이터 수정에 대한 @Version 사용으로 추가 확인
4. db 값을 통한 비교 수행으로 하루 이전 생성에 대한 부분 초대 수락 불가 

* 테스트 수행 방법 ( 포스트맨 활용 )

1. 샘플 멤버 추가 기능 (매니저 권한)
- POST : http://localhost:8080/member/create
- Body
  {
  "name": "manager",
  "phoneNumber": "010-1111-1234",
  "email": "manager@manager.com",
  "authority": "매니저"
  }

2. 그룹 초대 기능
- POST : http://localhost:8080/group/invite
- Body
  {
  "name": "test",
  "phoneNumber": "010-1111-1234",
  "email": "test@test.com",
  "authority": "초대예정자"
  }

3. 초대 수락 기능 
- POST : http://localhost:8080/group/accept
- Body
{
  "linkId":"1",
  "url":"https://testUrl.com",
  "title":"Join us our group",
  "content":"will you join us?",
  "useFlag":"false",
  "memberId":"1"
}
