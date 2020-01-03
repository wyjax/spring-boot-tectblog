package wyjax.techblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wyjax.techblog.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

}