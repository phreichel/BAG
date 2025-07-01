//**************************************************************************************************
package phi.devmate.document;
//**************************************************************************************************

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//**************************************************************************************************
@Entity(name="Document")
@Table(name="DOCUMENT")
public class Document {

	//==============================================================================================
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="ID")
	@Getter
	@Setter
	private UUID id;
	//==============================================================================================

	//==============================================================================================
	@Column(name="IDENT", length=255, nullable=false)
	@Getter
	@Setter
	private String ident;
	//==============================================================================================

	//==============================================================================================
	@Column(name="SUBJECT", nullable=false)
	@Getter
	@Setter
	private String subject;
	//==============================================================================================

}
//**************************************************************************************************
