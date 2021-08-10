package trade.transmission;

import java.util.Date;

/**
 * @author vandanaverma
 */
public class TradeDetail {

    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private Date maturityDate;
    private Date createdDate;
    private char expired;

    public TradeDetail(String tradeId, int version, String counterPartyId, String bookId, Date maturityDate, Date createdDate, char expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;
    }

    /**
     * @return the tradeId
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return the counterPartyId
     */
    public String getCounterPartyId() {
        return counterPartyId;
    }

    /**
     * @return the maturityDate
     */
    public Date getMaturityDate() {
        return maturityDate;
    }

    /**
     * @param maturityDate the maturityDate
     */
    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @return the expired
     */
    public char getExpired() {
        return expired;
    }

    /**
     * @param expired the expired to set
     */
    public void setExpired(char expired) {
        this.expired = expired;
    }

}
