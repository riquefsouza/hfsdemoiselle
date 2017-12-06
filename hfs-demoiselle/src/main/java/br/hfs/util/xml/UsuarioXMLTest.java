package br.hfs.util.xml;

import java.io.InputStream;
import java.util.Date;

import br.hfs.domain.Usuario;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class UsuarioXMLTest {

	/*
select t.eperson_id "id", t.email "login", t.password "senha", 
trim(t.firstname || ' ' || t.lastname) "nome", t.email "email", 
1 "idUsuarioInclusao", sysdate "dataInclusao"
from EPERSON t
where t.email is not null
and t.password is not null
and t.firstname is not null
and t.lastname is not null
and length(t.email) >= 5
and length(t.password) >= 5
and length(t.firstname) >= 5
order by 1
	*/	
	
/*
 * colocar into no C:\Demoiselle\server\jboss-7.1\standalone\configuration\standalone.xml e ver o log do arquivo
 * 
            <logger category="org.hibernate">
                <level name="DEBUG"/>
            </logger>
            <logger category="org.hibernate.type">
                <level name="TRACE"/>
            </logger>
            <logger category="org.hibernate.SQL">
                <level name="DEBUG"/>
            </logger>
 	
 */
	
	public static void main(String[] args) {
		doXML();
	}

    public static InputStream getXMLStream(String arquivo) {
        String caminho = "/br/hfs/util/xml/" + arquivo;
        return UsuarioXMLTest.class.getResourceAsStream(caminho);
    }
	
	public static void doXML() {
		XStream xstream = new XStream(new StaxDriver());
		//xstream.alias("ROWDATA", Rowdata.class);
		//xstream.aliasField("ROW", Usuario.class, "row");
		xstream.processAnnotations(Rowdata.class);
				
		//Rowdata rd = (Rowdata)xstream.fromXML(new File("c:/Temp/usuario.xml"));
		Rowdata rd = (Rowdata)xstream.fromXML(getXMLStream("usuario.xml"));
		
		for (Usuario item: rd.getRow()) {
			System.out.println(item);	
		}							
	}
	
	public static void paraXML() {
		XStream xstream = new XStream(new StaxDriver());
		//xstream.alias("ROWDATA", Rowdata.class);
		//xstream.aliasField("ROW", Usuario.class, "row");
		xstream.processAnnotations(Rowdata.class);
				
		Usuario usuario1 = new Usuario(Long.valueOf(1), "henrique.souza", "abcd1234",
				"Henrique Figueiredo de Souza", 
				"henrique.souza@trt1.jus.br", "02685748474", null,
				Long.valueOf(1), new Date(),
				null, null);

		Usuario usuario2 = new Usuario(Long.valueOf(2), "OUTRO CARA", "abcd1234",
				"Henrique Figueiredo de Souza",
				"henrique.souza@trt1.jus.br", "02685748474", null,
				Long.valueOf(1), new Date(),
				null, null);
		
		Rowdata rd = new Rowdata(usuario1, usuario2);
		String xml = xstream.toXML(rd);
		System.out.println(xml);
	}
	
}
