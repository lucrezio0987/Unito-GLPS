public class Person {
	private String name;

	public Person(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object o) {
		boolean ris = false;
		if (o!=null && o instanceof Person) {
			Person p = (Person)o;
			ris = name.equals(p.name);
		}
		return ris;
	}
}
