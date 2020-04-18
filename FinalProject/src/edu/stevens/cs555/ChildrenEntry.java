package edu.stevens.cs555;

public class ChildrenEntry {
	private String name, birthday;

	public ChildrenEntry(String name, String birthday) {
		super();
		this.name = name;
		this.birthday = birthday;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ChildrenEntry)) {
			return false;
		}
		ChildrenEntry p = (ChildrenEntry) obj;
		return name.equals(p.name) && birthday.equals(p.birthday);

	}

	@Override
	public int hashCode() {
		return 31;
	}

	public String getBirthday() {
		return birthday;
	}

}
