package com.sparta.hhztclone.global.security;

import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import com.sparta.hhztclone.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(ErrorCode.NOT_FOUND_EMAIL.getMsg())
        );
        return new UserDetailsImpl(member);
    }
}
