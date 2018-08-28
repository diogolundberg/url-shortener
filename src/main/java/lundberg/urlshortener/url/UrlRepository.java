package lundberg.urlshortener.url;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UrlRepository extends CrudRepository<Url, String> {
}
