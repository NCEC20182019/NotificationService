package nc.project.repository;

import nc.project.model.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer > {
  Template getTemplateById(Integer id);
}
