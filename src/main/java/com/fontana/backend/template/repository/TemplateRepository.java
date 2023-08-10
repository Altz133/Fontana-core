package com.fontana.backend.template.repository;

import com.fontana.backend.template.entity.Template;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TemplateRepository extends JpaRepository<Template,Integer> {
    List<Template> findAllById(int id, Pageable pageable);


}
