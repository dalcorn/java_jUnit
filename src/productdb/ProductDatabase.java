package productdb;

/*
 * hw4 Darlene Alcorn
 */



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ProductDatabase implements ProductDB {

	private static Integer id = 155;
	
	public List<Product> data = Collections.synchronizedList(new ArrayList<Product>());

	/**
	 * Retrieve product by primary key
	 * 
	 * @param productId
	 * @return null if not found
	 */

	@Override
	public Product getProduct(int productId) {
		ListIterator<Product> itr = data.listIterator();
		while (itr.hasNext()) {
			Product p = itr.next();

			if (productId == p.getId()) {
				//System.out.println("Item requested: " + p);
				return p;
			}
		}
		System.out.println("No product with that id.");
		return null;
	}

	/**
	 * Retrieve products by department code
	 * 
	 * @param code
	 * @return empty list if none found
	 */
	@Override
	public List<Product> getProductsByDept(DeptCode code) {

		List<Product> deptList = new ArrayList<Product>();

		for (Product item : data) {

			if (code.equals(item.getDept())) {
				deptList.add(item);
				System.out.println("\nProducts in department " + item.getDept()
						+ ":  " + item);
				return deptList;

			} else {
			}
		}
		System.out.printf("Department %s is empty. ", code);
		return deptList; 
	}

	/**
	 * Retrieve all products in database
	 * 
	 * @return empty list if no products in database
	 */
	@Override
	public List<Product> getAllProducts() {

		if (data.isEmpty()) {
			System.out.println("No products in the database.");
			return data;
		} else {
			System.out.println("List of all products: ");
			for (Product p : data) {
				System.out.println(p);

			}
			return data;
		}
	}

	/**
	 * Add to product to database
	 * 
	 * @param p
	 * 
	 * @throws ProductAlreadyExistsException
	 *             if there is already a product with same name
	 */
	@Override
	public void addProduct(Product product)
			throws ProductAlreadyExistsException {
		
		ListIterator<Product> itr = data.listIterator();

		while (itr.hasNext()) {
			Product p = itr.next();

			if (product.getName().equals(p.getName())) {
			
				System.out.printf("Product: %s already in database.", product);
				 //try {
				throw new ProductAlreadyExistsException("pnfe");
				 //} catch (ProductAlreadyExistsException e) {}
			}			
		}	
		id++;
		product.setId(id);
		data.add(product);
	
	}

	/**
	 * Update product in database with given information
	 * 
	 * @param p
	 * @throws ProductNotFoundException
	 *             if can't find given product by id
	 */
	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
	

		for (Product p : data) {

			if (product.getId() == p.getId()) {

				System.out.println("Updated product: " + p);
			}
		}
		if (!data.contains(product)) {
			// try {
			throw new ProductNotFoundException("pnfe");
			// } catch (ProductNotFoundException e) {}
		}
	}

	/**
	 * Remove product from database by product id
	 * 
	 * @param productId
	 * @throws ProductNotFoundException
	 *             if can't find given product by id
	 */

	@Override
	public void deleteProduct(int productId) throws ProductNotFoundException {
		Product prod = getProduct(productId);
		if (prod == null) {
			//try {
			throw new ProductNotFoundException("pnfe");
			 //} catch (ProductNotFoundException e) {}
		} else {

			ListIterator<Product> itr = data.listIterator();
			while (itr.hasNext()) {
				Product p = itr.next();

				if (productId == p.getId()) {
					itr.remove();
					System.out.printf("\nItem: %s deleted.", p);
				}
			}
		}
	}

	/**
	 * Save the existing products in the database into a file. An implementation
	 * of this class decides which File I/O technique to use and the location of
	 * the file.
	 * 
	 * 
	 */
	@Override
	public void saveProductsToDisk() throws IOException {
	
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream ("persist.txt"))) {
			out.writeObject(data);
			
			System.out.println("\nWritten to file: " + data);
		}
		catch (IOException e){
			System.out.println("Exception during serialization: " + e);
		}
	}
	/**
	 * Load the products from a file. An implementation of this class decides
	 * where to read the products from.
	 * 
	 * @throws IOException
	 * 
	 * 
	 */

	@Override
	public void loadProductsFromDisk() throws IOException {
	
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("persist.txt")))
			{			
				data = (List<Product>) in.readObject();
			
				System.out.println("Read from file: " + data);
			}
			catch(Exception e) {
				System.out.println("Exception during deserailization: " + e);
			}
		}
	}

