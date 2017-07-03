//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.07.03 um 01:04:07 PM CEST 
//


package de.aoe.musicworld.pojo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MatchingReleases_QNAME = new QName("", "matchingReleases");
    private final static QName _Record_QNAME = new QName("", "record");
    private final static QName _Records_QNAME = new QName("", "records");
    private final static QName _Release_QNAME = new QName("", "release");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Record }
     * 
     */
    public Record createRecord() {
        return new Record();
    }

    /**
     * Create an instance of {@link Releases }
     * 
     */
    public Releases createReleases() {
        return new Releases();
    }

    /**
     * Create an instance of {@link Records }
     * 
     */
    public Records createRecords() {
        return new Records();
    }

    /**
     * Create an instance of {@link Release }
     * 
     */
    public Release createRelease() {
        return new Release();
    }

    /**
     * Create an instance of {@link Record.Tracklisting }
     * 
     */
//    public Record.Tracklisting createRecordTracklisting() {
//        return new Record.Tracklisting();
//    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Releases }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "matchingReleases")
    public JAXBElement<Releases> createMatchingReleases(Releases value) {
        return new JAXBElement<Releases>(_MatchingReleases_QNAME, Releases.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Record }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "record")
    public JAXBElement<Record> createRecord(Record value) {
        return new JAXBElement<Record>(_Record_QNAME, Record.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Records }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "records")
    public JAXBElement<Records> createRecords(Records value) {
        return new JAXBElement<Records>(_Records_QNAME, Records.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Release }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "release")
    public JAXBElement<Release> createRelease(Release value) {
        return new JAXBElement<Release>(_Release_QNAME, Release.class, null, value);
    }

}
