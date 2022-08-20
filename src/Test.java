import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
	
	private Field[]      fieldAttributesArray;
	private List<String> fieldAttributesList;
	private List<String> fieldAttributesValuesList;
	
	private Person person;
	
	public Test()
	{
		super();
		this.person = new Person();
		this.fieldAttributesList = new ArrayList<>();
		this.fieldAttributesValuesList= new ArrayList<>();
	}
	
	public Field[] getFieldAttributesArray() {
		return fieldAttributesArray;
	}
	public void setFieldAttributesArray(Field[] fieldAttributesArray) {
		this.fieldAttributesArray = fieldAttributesArray;
	}
	public List<String> getFieldAttributesList() {
		return fieldAttributesList;
	}
	public void setFieldAttributesList(List<String> fieldAttributesList) {
		this.fieldAttributesList = fieldAttributesList;
	}
	public List<String> getFieldAttributesValuesList(){
		return fieldAttributesValuesList;
	}
	public void setFieldAttributesValuesList(List<String> fieldAttributesValuesList) {
		this.fieldAttributesValuesList = fieldAttributesValuesList;
	}
	public Person getPerson() {
		return person;
	}

	public static void main(String[] args) {
		
		Test test = new Test();
		
		initializePerson(test.getPerson());
		test.setFieldAttributesList(getFieldAttributes(Person.class));
		System.out.println(test.getFieldAttributesList());
		test.setFieldAttributesValuesList(getFieldAttributesValue(test.getPerson(), test.getFieldAttributesList()));
		System.out.println(test.getFieldAttributesValuesList());
	}
	
	private static void initializePerson(Person person)
	{
		person.setFirstName("Can");
		person.setMidName("");
		person.setLastName("Demirhan");
		person.setAge(28);
		person.setPhoneNumber("555-xxx");
		person.setAddress("Seyhan");
	}
	private static List<String> getFieldAttributes(Class<?> clasz)
	{
		Field[] fieldArr = Person.class.getDeclaredFields();
		System.out.println(Arrays.deepToString(fieldArr));
		
		List<String> fieldAttributesList = Arrays
				.stream(fieldArr)
				.map(s -> s.toString().split(" ")[2].split("[.]",0)[1])
				.collect(Collectors.toList());
		return fieldAttributesList;
	}
	private static List<String> getFieldAttributesValue(Person person, List<String> fieldAttributesList)
	{
		List<String> fieldAttributesValuesList = new ArrayList<>();
		
		for (String fieldAtt : fieldAttributesList) 
		{
			try {
				PropertyDescriptor propDesc = new PropertyDescriptor(fieldAtt, person.getClass());
				fieldAttributesValuesList.add(propDesc.getReadMethod().invoke(person).toString());
				
			} catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fieldAttributesValuesList;
	}
}
