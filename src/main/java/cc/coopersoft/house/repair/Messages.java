package cc.coopersoft.house.repair;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * Created by cooper on 6/8/16.
 */
@MessageBundle
public interface Messages {

    @MessageTemplate("{payment_notice_not_fount}")
    String paymentNoticeNotFound(String noticeNumber);

    @MessageTemplate("{api_server_fail}")
    String apiServerError();

    @MessageTemplate("{payment_notice_is_using}")
    String paymentNoticeIsUsing();
}
