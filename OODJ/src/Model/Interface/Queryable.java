package Model.Interface;

/**
 *
 * @author CCK
 */
public interface Queryable {

    /**
     * Model should be queryable by where operator
     */
    public Object where(String type, String queryOperator, String queryString);

    /**
     * Model should be queryable by where
     */
    public Object where(String type, String queryString);

    /**
     * Model should be get All
     */
    public Object all();
}
