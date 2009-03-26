package testdata;
import domain.Postcode;


public class PostcodeBuilder {

	private String sector = "NW6";
	private String district = "3AA";

	public Postcode build() {
		return new Postcode(district, sector);
	}

	public PostcodeBuilder withDistrict(String district) {
		this.district = district;
		return this;
	}

	public PostcodeBuilder withSector(String sector) {
		this.sector = sector;
		return this;
	}

}
