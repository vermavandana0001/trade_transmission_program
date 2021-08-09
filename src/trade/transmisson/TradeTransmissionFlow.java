/**
 * 
 */
package trade.transmisson;

import java.util.Date;

/**
 * @author vandanaverma
 *
 */
public class TradeTransmissionFlow {

	// add Trade
	public void addTradeDetail(TradeDetail tradeDetail) throws Exception {
		// TODO

	}

	// get trade by tradeId
	public TradeDetail getTradeDeatil(String tradeId) throws Exception {
		// TODO
		return null;

	}

	// If the lower version is being received by the store then it will reject the
	// trade and throw an exception.
	public void validateVersion(TradeDetail tradeDetails, int version) throws Exception {
		// TODO

	}

	// Store should not allow the trade which has less maturity date than current
	// date
	public boolean validateMaturityDate(Date maturityDate, Date CurrentDate) {
		// TODO

		return Boolean.FALSE;

	}

	// Update expired flag if the trade crosses the maturity date
	public void updateTradeDetailExpiredFlag() {
		// TODO

	}

}
