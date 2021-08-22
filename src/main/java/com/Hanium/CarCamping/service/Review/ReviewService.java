package com.Hanium.CarCamping.service.Review;

import com.Hanium.CarCamping.Exception.NoSuchMemberException;
import com.Hanium.CarCamping.Exception.NoSuchReviewException;
import com.Hanium.CarCamping.Exception.NotReviewWriterException;
import com.Hanium.CarCamping.domain.dto.review.CreateReviewDto;
import com.Hanium.CarCamping.domain.entity.CampSite;
import com.Hanium.CarCamping.domain.entity.Review;
import com.Hanium.CarCamping.domain.entity.member.Member;
import com.Hanium.CarCamping.repository.MemberRepository;
import com.Hanium.CarCamping.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Long saveReview(CreateReviewDto createReviewDto, Member writer, CampSite campSite) {
        Review save = reviewRepository.save(Review.createReview(createReviewDto, writer, campSite));
        return save.getReview_id();
    }

    public List<Review> getCampSiteReviewByDateDESC(Long campSite_id) {
        return reviewRepository.findByCampSiteDateDESC(campSite_id);
    }
    public List<Review> getCampSiteReviewByDateASC(Long campSite_id) {
        return reviewRepository.findByCampSiteDateASC(campSite_id);
    }
    public List<Review> getCampSiteReviewByScoreDESC(Long campSite_id) {
        return reviewRepository.findByCampSiteDESC(campSite_id);
    }
    public List<Review> getCampSiteReviewByScoreASC(Long campSite_id) {
        return reviewRepository.findByCampSiteASC(campSite_id);
    }

    public Review getReview(Long id) {
        return reviewRepository.getById(id);
    }

    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }
    public List<Review> findByCampSite(Long campsite_id){
        return reviewRepository.findReviewByCampSite(campsite_id);
    }
    @Transactional
    public void deleteReview(String email,Long review_id) {
        Review review= reviewRepository.findById(review_id).orElseThrow(NoSuchReviewException::new);
        Member result= memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        if (!result.getId().equals(review.getWriter().getId())) {
            throw new NotReviewWriterException("리뷰 작성자가 아닙니다");
        }
        reviewRepository.delete(review);
    }
    public List<Review> mostRecommendedTop3Review(CampSite campsite) {
        return reviewRepository.findTop3ByCampSiteOrderByRecommendDesc(campsite);
    }
}
