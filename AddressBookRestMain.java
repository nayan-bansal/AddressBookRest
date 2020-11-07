package addresbookrest.address;

import java.util.ArrayList;
import java.util.List;

public class AddressBookRestMain {

	private List<Contact> contactList;

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO;
	}

	public AddressBookRestMain() {
	}

	public AddressBookRestMain(List<Contact> contactList) {
		this();
		this.contactList = new ArrayList<>(contactList); 

	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.REST_IO))
			return contactList.size();
		return 0;
	}

	public void addContactToAddressBook(Contact contactData, IOService restIo) {
		contactList.add(contactData);
		System.out.println(contactList.size());
	}

	public Contact getEmployeeData(String name) {
		return contactList.stream().filter(contact->contact.name.equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public void updateContactSalary(String name, double salary, IOService ioService) {
		Contact contact=getEmployeeData(name);
		if(contact!=null)
			contact.salary=salary;
	}
}
