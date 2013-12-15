package productdb;

import productdb.util.Assert;

/**
 * This class exercises all the methods in the @link{ProductDB} interface
 *  
 * @author hluu
 *
 */
public class ProductDBClient {

	public static void main(String[] args) throws Exception {
		
		ProductDB productDB = null;
		productDB = new ProductDatabase();
		
		productDB.loadProductsFromDisk();  
		
		System.out.println("\n########################");
		testProductServer(productDB);
		
		System.out.println("\n########################");	
		productDB.saveProductsToDisk(); 
		
	}

	/**
	 * DO NOT MODIFY ANYTHING IN THIS METHOD.  THIS METHOD SHOULDN'T THROW
	 * ANY EXCEPTION IF PRODUCT DB SERVER WAS IMPLENTED ACCORDING TO ProductDB
	 * INTERFACE 
	 * 
	 * @param productDB
	 * @throws ProductAlreadyExistsException
	 * @throws ProductNotFoundException
	 */
	private static void testProductServer(ProductDB productDB)
			throws ProductAlreadyExistsException, ProductNotFoundException {
		
		Product ipod = new Product("ipod", 125.0, DeptCode.ELECTRONICS);
		System.out.println(ipod);
		
		Assert.assertNotNull(productDB.getAllProducts());
		
		Assert.assertNotNull(productDB.getProductsByDept(DeptCode.ELECTRONICS));
		
		int beforeTotalCount = productDB.getAllProducts().size();
		int beforeDeptCount = productDB.getProductsByDept(DeptCode.ELECTRONICS).size();
		System.out.println("");
		productDB.addProduct(ipod);
		
		
		
		System.out.println("\n********* start testing ************");
		Assert.assertNotNull(ipod.getId());
		Assert.assertNotNull(productDB.getProduct(ipod.getId()));
		Assert.assertNotNull(productDB.getAllProducts());
		
		Assert.assertEquals(productDB.getAllProducts().size(), beforeTotalCount+1);
		//Assert.assertEquals(productDB.getProductsByDept(ipod.getDept()).size(), beforeDeptCount +1);
		
		double newPrice = ipod.getPrice() + Math.random() * 100;
		ipod.setPrice(newPrice);
		productDB.updateProduct(ipod);
		
		Assert.assertEquals(newPrice, productDB.getProduct(ipod.getId()).getPrice());
		
		try {
			productDB.addProduct(ipod);
			Assert.fail("should've gotten ProductAlreadyExistsException");  
		} catch (ProductAlreadyExistsException pae) {
			// expecting this
		}
		
		productDB.deleteProduct(ipod.getId());
		Assert.assertNotNull(ipod.getId());
		
		
		ipod.setId(Integer.MAX_VALUE);
		try {
			productDB.updateProduct(ipod);
			Assert.fail("should've gotten ProductNotFoundException");
		} catch (ProductNotFoundException pnfe) {
			// expecting this
		}
		
		try {
			productDB.deleteProduct(ipod.getId());
			Assert.fail("should've gotten ProductNotFoundException");
		} catch (ProductNotFoundException pnfe) {
			// expecting this
		}
		System.out.println("********* done testing ************");

	}

}
