package toy.com.post.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import toy.com.post.repository.PostRepository;

@Service
@Transactional
public class PostWriteService {

	private PostRepository postRepository;

	// 추후 포스트 게시글 저장 api 생성 후 진행
	public void savePost() {

	}

}
