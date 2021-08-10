package trade.transmission;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;

import static java.lang.System.out;

public class TradeTransmissionFlow {

    HashMap<String, TradeDetail> tradeDetailsMap = new HashMap<>();

    // addition of trade details
    public void addTradeDetail(TradeDetail tradeDetail) throws Exception {
        if (tradeDetailsMap.containsKey(tradeDetail.getTradeId())) {
            validateVersion(tradeDetail, tradeDetailsMap.get(tradeDetail.getTradeId()).getVersion());

            if (validateMaturityDate(tradeDetail.getMaturityDate(), tradeDetailsMap.get(tradeDetail.getTradeId()).getMaturityDate())) {
                tradeDetailsMap.replace(tradeDetail.getTradeId(), tradeDetail);
                out.println(MessageFormat.format("Trade with Id {0} added to the Store.", tradeDetail.getTradeId()));
            } else {
                out.println(MessageFormat.format("Trade with Id {0} is not added in the store as maturity date is less than current date", tradeDetail.getTradeId()));
            }
        } else {

            if (validateMaturityDate(tradeDetail.getMaturityDate(), tradeDetail.getCreatedDate())) {

                tradeDetailsMap.put(tradeDetail.getTradeId(), tradeDetail);
                out.println(MessageFormat.format("Trade with Id {0} is added to the Store", tradeDetail.getTradeId()));

            } else {
                out.println(MessageFormat.format("Trade with Id {0} is not added in the store as maturity date is less than current date",tradeDetail.getTradeId()));
            }
        }


    }

    // get tradeDetail by tradeId
    public TradeDetail getTradeDetail(String tradeId) throws Exception {
        if(tradeDetailsMap.containsKey(tradeId))
            return tradeDetailsMap.get(tradeId);
        throw new Exception ("Trade with "+tradeId+" not Found");

    }

    // If the lower version is being received by the store then it will reject the
    // trade and throw an exception.
    public void validateVersion(TradeDetail tradeDetail, int version) throws Exception {
        if (tradeDetail.getVersion() < version)
            throw new Exception(tradeDetail.getVersion() + " is less than " + version);
    }

    // Store should not allow the trade which has less maturity date than current
    // date
    public boolean validateMaturityDate(Date maturityDate, Date currentDate) {

        return currentDate.compareTo(maturityDate) <= 0;
    }

    // Update expired flag if the trade crosses the maturity date
    public void updateTradeDetailExpiredFlag() {
        Date currentDate = new Date();
        tradeDetailsMap.keySet().stream().filter(record -> currentDate.compareTo(tradeDetailsMap.get(record).getMaturityDate()) > 0).forEach(record -> {
            TradeDetail t = tradeDetailsMap.get(record);
            t.setExpired('Y');
            tradeDetailsMap.replace(record, t);
        });

    }

}
