package wyjax.techblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wyjax.techblog.model.Content;
import wyjax.techblog.repository.ContentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    @Transactional
    public Page<Content> getBoardList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber());
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return contentRepository.findByIsdelete(pageable, 'N');
    }

    @Transactional
    public Content save(String title, String content, String uid) {
        Content cnt = new Content();
        cnt.setTitle(title);
        cnt.setContent(content);
        cnt.setUid(uid);
        cnt.setRegdate(LocalDateTime.now());
        cnt.setViewcount(0);
        cnt.setIsdelete('N');
        Content newcontent = contentRepository.save(cnt);
        return newcontent;
    }

    public Content view(Long id) {
        return contentRepository.findByIdAndAndIsdelete(id, 'N');
    }

    @Transactional
    public void delete(Long id) {
        Content content = contentRepository.getOne(id);
        content.setIsdelete('Y');
    }

    public Boolean userCheck(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginid = auth.getName();
        String uid = view(id).getUid();

        if (!loginid.equals(uid)) {
            return false;
        }
        return true;
    }
}