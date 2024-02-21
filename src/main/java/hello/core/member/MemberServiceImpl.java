package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 아니 기존에는 빈 설정 정보에 의존 관계를 명시해줄 수 있었는데
    // 자동으로 빈 등록해주는 컴포넌트 스캔 방식을 사용하면 의존 관계 주입 어떻게하지?
    // 자동 의존관계 주입 해주는 @Autowired 사용하면 된다.
    // 의존관계가 있는 타입의 빈을 자동으로 연결해준다.
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
