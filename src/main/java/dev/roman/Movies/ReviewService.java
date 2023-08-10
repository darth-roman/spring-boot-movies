package dev.roman.Movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId){
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review)).first();

        return review;

    }

//    public Object updateReview(Long id, String newReview) {
//        Review review = reviewRepository.updateReview(id, newReview);
//
//        Query query = new Query().addCriteria(Criteria.where("_id.timestamps").is(id));
//        FindAndReplaceOptions options = new FindAndReplaceOptions().upsert().returnNew();
//
//
////        mongoTemplate.update(Review.class)
////                .matching(Criteria.where("_id.timestamps").is(id))
////                .apply(new Update())
//        return mongoTemplate.findAndReplace(query, newReview, options, Review.class, "review", Review.class);
//    }

    }
