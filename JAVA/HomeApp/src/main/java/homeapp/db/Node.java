//*****************************************************************************
package homeapp.db;
//*****************************************************************************

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//*****************************************************************************
@Entity(name = "Node")
@Table(name = "node")
public class Node {

	//=========================================================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	//=========================================================================

	//=========================================================================
	@Column(name = "label", unique = true, nullable = false)
	public String label;
	//=========================================================================

	//=========================================================================
	@Column(name = "quantity", unique = false, nullable = false)
	public Integer quantity = 1;
	//=========================================================================

	//=========================================================================
	@Column(name = "serialnr", unique = false, length = 255, nullable = true)
	public String serialNumber;
	//=========================================================================

	//=========================================================================
	@OneToMany(mappedBy = "source")
	public List<Link> sourceLinks;	
	//=========================================================================

	//=========================================================================
	@OneToMany(mappedBy = "target")
	public List<Link> targetLinks;	
	//=========================================================================
	
}
//*****************************************************************************
