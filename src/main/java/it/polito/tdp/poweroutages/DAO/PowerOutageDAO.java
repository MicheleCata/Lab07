package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getPowerForNerc (String nomeNerc) {
		String sql = "SELECT p.id, p.customers_affected, p.date_event_began, p.date_event_finished,p.nerc_id "
					+"From PowerOutages p , Nerc n "
					+"where p.nerc_id=n.id AND n.value= ?";
		
		List<PowerOutage> listaPower = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nomeNerc);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				PowerOutage p = new PowerOutage(res.getInt("p.id"), res.getInt("p.customers_affected"), res.getTimestamp("p.date_event_began").toLocalDateTime(),
								res.getTimestamp("p.date_event_finished").toLocalDateTime(),res.getInt("p.nerc_id"));
				listaPower.add(p);
			}
			conn.close();

	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
		
		return listaPower;
	}
	
	

}
