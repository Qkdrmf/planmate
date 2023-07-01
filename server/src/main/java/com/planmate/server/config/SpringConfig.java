package com.planmate.server.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.planmate.server.repository.*;
import com.planmate.server.service.member.MemberService;
import com.planmate.server.service.member.MemberServiceImpl;
import com.planmate.server.service.post.PostService;
import com.planmate.server.service.post.PostServiceImpl;
import com.planmate.server.service.s3.S3UploadService;
import com.planmate.server.service.s3.S3UploaderServiceImpl;
import com.planmate.server.service.token.TokenService;
import com.planmate.server.service.token.TokenServiceImpl;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
/**
 * @author 지승언
 * service 로직에 대한 bean을 직접 등록하는 파일이다
 * 직접 등록하는 이유는 나중에 서비스 교체할 일이 있을 경우와 유지보수를 위해서이다
 * */
@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final PostRepository postRepository;
    private PostTagRepository postTagRepository;
    private final MemberScrapRepository memberScrapRepository;
    private final AmazonS3Client amazonS3Client;

    @Autowired
    public SpringConfig(final MemberRepository memberRepository,
                        final TokenRepository tokenRepository,
                        final PostRepository postRepository,
                        final PostTagRepository postTagRepository,
                        final MemberScrapRepository memberScrapRepository, final AmazonS3Client amazonS3Client) {
        this.memberRepository = memberRepository;
        this.tokenRepository = tokenRepository;
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
        this.memberScrapRepository = memberScrapRepository;
        this.amazonS3Client = amazonS3Client;
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository, tokenRepository);
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl(tokenRepository, memberRepository);
    }

    @Bean
    public PostService postService() {
        return new PostServiceImpl(postRepository,postTagRepository,memberRepository,memberScrapRepository);
    }

    @Bean
    public S3UploadService S3UploadService() {
        return new S3UploaderServiceImpl(amazonS3Client);
    }
}