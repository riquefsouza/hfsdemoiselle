package br.hfs.util.xml;

import java.io.InputStream;
import java.util.List;

import br.hfs.domain.Usuario;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public final class UsuarioXML {

    private static InputStream getXMLStream(String arquivo) {
        String caminho = "/br/hfs/util/xml/" + arquivo;
        return UsuarioXMLTest.class.getResourceAsStream(caminho);
    }
	
	public static List<Usuario> carregar(String xml) {
		XStream xstream = new XStream(new StaxDriver());
		//xstream.alias("ROWDATA", Rowdata.class);
		//xstream.aliasField("ROW", Usuario.class, "row");
		xstream.processAnnotations(Rowdata.class);
				
		//Rowdata rd = (Rowdata)xstream.fromXML(new File("c:/Temp/usuario.xml"));
		Rowdata rd = (Rowdata)xstream.fromXML(getXMLStream(xml + ".xml"));
		
		return rd.getRow();		
	}
	
}
