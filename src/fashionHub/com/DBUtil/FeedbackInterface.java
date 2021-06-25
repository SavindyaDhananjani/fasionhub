package fashionHub.com.DBUtil;

import java.util.List;

import fashionHub.com.Object.Feedback;

public interface FeedbackInterface {
	int deleteFeedback(String id);
	List showFeedback();
	String sendFeedback(Feedback fb);
}
