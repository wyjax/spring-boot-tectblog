package wyjax.techblog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wyjax.techblog.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findByIsdelete(Pageable pageable, char check);

    Content findByIdAndAndIsdelete(Long id, char isdelete);
}