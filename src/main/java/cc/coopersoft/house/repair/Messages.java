package cc.coopersoft.house.repair;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

/**
 * Created by cooper on 6/8/16.
 */
@MessageBundle
public interface Messages {

    @MessageTemplate("{payment_notice_not_found}")
    String paymentNoticeNotFound(String noticeNumber);

    @MessageTemplate("{api_server_fail}")
    String apiServerError();

    @MessageTemplate("{payment_notice_is_using}")
    String paymentNoticeIsUsing();

    @MessageTemplate("{house_not_found}")
    String houseNotFound(String code);

    @MessageTemplate("{owner_business_not_found}")
    String ownerBusinessNotFound(String businessId);

    @MessageTemplate("{contract_not_found}")
    String contractNotFound(String contractId);

    @MessageTemplate("{account_join_to_repair}")
    String accountJoin(int count);

    @MessageTemplate("{account_not_found")
    String accountNotFound();

    @MessageTemplate("{report_export_error}")
    String reportError();
}
