//**************************************************************************************************
package phi.devmate.paragraph;
//**************************************************************************************************

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import phi.devmate.document.Document;

//**************************************************************************************************
@Entity(name="Paragraph")
@Table(name="PARAGRAPH")
public class Paragraph {

	//==============================================================================================
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(name="ID")
	@Getter
	@Setter
	private UUID id;
	//==============================================================================================

	//==============================================================================================
	@ManyToOne(optional=false)
	@JoinColumn(name="SOURCE_ID", nullable=false)
	@Getter
	@Setter
	private Document source_id;
	//==============================================================================================
	
	//==============================================================================================
	@Column(name="IDENT", length=255, nullable=false)
	@Getter
	@Setter
	private String ident;
	//==============================================================================================

	//==============================================================================================
	@Column(name="ORDER", nullable=false)
	@Getter
	@Setter
	private Integer order;
	//==============================================================================================
	
	//==============================================================================================
	@Column(name="SUBJECT", nullable=false)
	@Getter
	@Setter
	private String subject;
	//==============================================================================================

}
//**************************************************************************************************
