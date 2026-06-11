//*****************************************************************************
package homeapp.db;
//*****************************************************************************

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//*****************************************************************************
@Entity(name = "ItemType")
@Table(name = "itemtype")
public class ItemType {

    //=========================================================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	//=========================================================================

	//=========================================================================
	@Column(name = "ident", unique = true, length = 255, nullable = false)
	public String ident;
	//=========================================================================

	//=========================================================================
	@OneToMany
	public List<ItemText> entries = new ArrayList<>();
	//=========================================================================

}
//*****************************************************************************
