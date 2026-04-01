//*****************************************************************************
package homeapp.sv;
//*****************************************************************************

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import homeapp.db.Node;
import homeapp.db.NodeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

//*****************************************************************************
@Service
public class NodeService {

	//=========================================================================
	private NodeRepository nodeRepository;
	//=========================================================================
	
	//=========================================================================
	public NodeService(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}
	//=========================================================================

	//=========================================================================
	@Transactional
	@PostConstruct
	private void construct() {
		Node node = new Node();
		node.label = "HELLO!" + UUID.randomUUID();
		node.serialNumber = "GAGAGAGT";
		nodeRepository.save(node);
	}
	//=========================================================================

	//=========================================================================
	@Transactional
	public void getAll(List<NodeDTA> list) {
		var allNodes = this.nodeRepository.findAll();
		for (var node : allNodes) {
			var nodeDTA = new NodeDTA();
			nodeDTA.set(node);
			list.add(nodeDTA);
		}
	}
	//=========================================================================

	//=========================================================================
	@Transactional
	public void getTree() {
		//TBD
		//throw new UnimplementedException();
	}
	//=========================================================================


}
//*****************************************************************************
