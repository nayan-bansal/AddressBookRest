package addresbookrest.address;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import addresbookrest.address.AddressBookRestMain;
import addresbookrest.address.AddressBookRestMain.IOService;
import addresbookrest.address.Contact;


public class AddressBookRestTest {

	private static Logger log = Logger.getLogger(AddressBookRestTest.class.getName());
	private static AddressBookRestMain addressBookService = null;

	public Contact[] getContactList() {
		Response response = RestAssured.get("/contacts");
		log.info("Contact entries in JSON Server :\n" + response.asString());
		Contact[] arrayOfEmps = new Gson().fromJson(response.asString(), Contact[].class);
		return arrayOfEmps;
	}

	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 4000;
	}

	@Test
	public void givenEmployeeDataInJSONServer_WhenRetrieved_ShouldmatchTheCount() {
		Contact[] arrayOfContacts = getContactList();
		addressBookService = new AddressBookRestMain(Arrays.asList(arrayOfContacts));
		long entries = addressBookService.countEntries(IOService.REST_IO);
		Assert.assertEquals(6, entries);
	}
	
	@Test
	public void givenNewEmployee_WhenAddedInJsonServer_ShouldMatchResponseAndCount() {
		Contact[] arrayOfEmps = getContactList();
		addressBookService = new AddressBookRestMain(Arrays.asList(arrayOfEmps));
		Contact contactData = new Contact(0, "Mark", "M", 2000000, LocalDate.now());
		Response response = addContactToJSONServer(contactData);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		contactData = new Gson().fromJson(response.asString(), Contact.class);
		addressBookService.addContactToAddressBook(contactData, IOService.REST_IO);
		long entries = addressBookService.countEntries(IOService.REST_IO);
		Assert.assertEquals(7, entries);
	}
	
}
