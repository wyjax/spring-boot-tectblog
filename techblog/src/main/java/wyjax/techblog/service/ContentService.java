package wyjax.techblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wyjax.techblog.model.Content;
import wyjax.techblog.repository.ContentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public Page<Content> getBoardList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber());
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return contentRepository.findAll(pageable);
    }

    public Content save(String title, String content, String uid) {
        Content cnt = new Content();
        cnt.setTitle(title);
        cnt.setContent(content);
        cnt.setUid(uid);
        cnt.setRegdate(LocalDateTime.now());
        Content newcontent = contentRepository.save(cnt);
        return newcontent;
    }
}