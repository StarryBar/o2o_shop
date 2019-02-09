package o2o.exceptions;

public class ProductOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3771376928381387603L;

	public ProductOperationException(String msg){
		super(msg);
	}
}
