package org.elasticsearch.search.aggregations.bucket.terms;

/**
 * Util class made to access private data from Buckets
 */
public class BucketUtil {
    public static final String module = BucketUtil.class.getName();
    /**
     * Get the name of the bucket
     * @param termsBucket
     * @return
     */
    public static String getKey(Object termsBucket) {
        if(termsBucket instanceof DoubleTerms.Bucket) {
            return ((DoubleTerms.Bucket) termsBucket).getKeyAsString();
        } else {
            return ((StringTerms.Bucket) termsBucket).getKeyAsString();
        }
    }

    /**
     * Get the document count for given bucket
     * @param termsBucket
     * @return
     */
    public static Long getCount(Object termsBucket) {
        if(termsBucket instanceof DoubleTerms.Bucket) {
            return ((DoubleTerms.Bucket) termsBucket).getDocCount();
        } else {
            return ((StringTerms.Bucket) termsBucket).getDocCount();
        }
    }
}
