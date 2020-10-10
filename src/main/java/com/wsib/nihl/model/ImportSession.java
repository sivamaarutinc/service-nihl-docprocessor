package com.wsib.nihl.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "batches" })
@XmlRootElement(name = "ImportSession")
public class ImportSession {

	@XmlElement(name = "Batches", required = true)
	protected ImportSession.Batches batches;

	/**
	 * Gets the value of the batches property.
	 * 
	 * @return possible object is {@link ImportSession.Batches }
	 * 
	 */
	public ImportSession.Batches getBatches() {
		return batches;
	}

	/**
	 * Sets the value of the batches property.
	 * 
	 * @param value allowed object is {@link ImportSession.Batches }
	 * 
	 */
	public void setBatches(ImportSession.Batches value) {
		this.batches = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within
	 * this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="Batch"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="BatchFields"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="BatchField" maxOccurs="unbounded" minOccurs="0"&gt;
	 *                               &lt;complexType&gt;
	 *                                 &lt;simpleContent&gt;
	 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
	 *                                     &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                     &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                   &lt;/extension&gt;
	 *                                 &lt;/simpleContent&gt;
	 *                               &lt;/complexType&gt;
	 *                             &lt;/element&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                   &lt;element name="Documents"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Document" maxOccurs="unbounded" minOccurs="0"&gt;
	 *                               &lt;complexType&gt;
	 *                                 &lt;complexContent&gt;
	 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                                     &lt;sequence&gt;
	 *                                       &lt;element name="IndexFields"&gt;
	 *                                         &lt;complexType&gt;
	 *                                           &lt;complexContent&gt;
	 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                                               &lt;sequence&gt;
	 *                                                 &lt;element name="IndexField" maxOccurs="unbounded" minOccurs="0"&gt;
	 *                                                   &lt;complexType&gt;
	 *                                                     &lt;simpleContent&gt;
	 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
	 *                                                         &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                                         &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                                       &lt;/extension&gt;
	 *                                                     &lt;/simpleContent&gt;
	 *                                                   &lt;/complexType&gt;
	 *                                                 &lt;/element&gt;
	 *                                               &lt;/sequence&gt;
	 *                                             &lt;/restriction&gt;
	 *                                           &lt;/complexContent&gt;
	 *                                         &lt;/complexType&gt;
	 *                                       &lt;/element&gt;
	 *                                       &lt;element name="Pages"&gt;
	 *                                         &lt;complexType&gt;
	 *                                           &lt;complexContent&gt;
	 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                                               &lt;sequence&gt;
	 *                                                 &lt;element name="Page"&gt;
	 *                                                   &lt;complexType&gt;
	 *                                                     &lt;simpleContent&gt;
	 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
	 *                                                         &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                                       &lt;/extension&gt;
	 *                                                     &lt;/simpleContent&gt;
	 *                                                   &lt;/complexType&gt;
	 *                                                 &lt;/element&gt;
	 *                                               &lt;/sequence&gt;
	 *                                             &lt;/restriction&gt;
	 *                                           &lt;/complexContent&gt;
	 *                                         &lt;/complexType&gt;
	 *                                       &lt;/element&gt;
	 *                                     &lt;/sequence&gt;
	 *                                     &lt;attribute name="FormTypeName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
	 *                                   &lt;/restriction&gt;
	 *                                 &lt;/complexContent&gt;
	 *                               &lt;/complexType&gt;
	 *                             &lt;/element&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "batch" })
	public static class Batches {

		@XmlElement(name = "Batch", required = true)
		protected ImportSession.Batches.Batch batch;

		/**
		 * Gets the value of the batch property.
		 * 
		 * @return possible object is {@link ImportSession.Batches.Batch }
		 * 
		 */
		public ImportSession.Batches.Batch getBatch() {
			return batch;
		}

		/**
		 * Sets the value of the batch property.
		 * 
		 * @param value allowed object is {@link ImportSession.Batches.Batch }
		 * 
		 */
		public void setBatch(ImportSession.Batches.Batch value) {
			this.batch = value;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within
		 * this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="BatchFields"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="BatchField" maxOccurs="unbounded" minOccurs="0"&gt;
		 *                     &lt;complexType&gt;
		 *                       &lt;simpleContent&gt;
		 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
		 *                           &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                           &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                         &lt;/extension&gt;
		 *                       &lt;/simpleContent&gt;
		 *                     &lt;/complexType&gt;
		 *                   &lt;/element&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *         &lt;element name="Documents"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Document" maxOccurs="unbounded" minOccurs="0"&gt;
		 *                     &lt;complexType&gt;
		 *                       &lt;complexContent&gt;
		 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                           &lt;sequence&gt;
		 *                             &lt;element name="IndexFields"&gt;
		 *                               &lt;complexType&gt;
		 *                                 &lt;complexContent&gt;
		 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                                     &lt;sequence&gt;
		 *                                       &lt;element name="IndexField" maxOccurs="unbounded" minOccurs="0"&gt;
		 *                                         &lt;complexType&gt;
		 *                                           &lt;simpleContent&gt;
		 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
		 *                                               &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                                               &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                                             &lt;/extension&gt;
		 *                                           &lt;/simpleContent&gt;
		 *                                         &lt;/complexType&gt;
		 *                                       &lt;/element&gt;
		 *                                     &lt;/sequence&gt;
		 *                                   &lt;/restriction&gt;
		 *                                 &lt;/complexContent&gt;
		 *                               &lt;/complexType&gt;
		 *                             &lt;/element&gt;
		 *                             &lt;element name="Pages"&gt;
		 *                               &lt;complexType&gt;
		 *                                 &lt;complexContent&gt;
		 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                                     &lt;sequence&gt;
		 *                                       &lt;element name="Page"&gt;
		 *                                         &lt;complexType&gt;
		 *                                           &lt;simpleContent&gt;
		 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
		 *                                               &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                                             &lt;/extension&gt;
		 *                                           &lt;/simpleContent&gt;
		 *                                         &lt;/complexType&gt;
		 *                                       &lt;/element&gt;
		 *                                     &lt;/sequence&gt;
		 *                                   &lt;/restriction&gt;
		 *                                 &lt;/complexContent&gt;
		 *                               &lt;/complexType&gt;
		 *                             &lt;/element&gt;
		 *                           &lt;/sequence&gt;
		 *                           &lt;attribute name="FormTypeName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
		 *                         &lt;/restriction&gt;
		 *                       &lt;/complexContent&gt;
		 *                     &lt;/complexType&gt;
		 *                   &lt;/element&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "batchFields", "documents" })
		public static class Batch {

			@XmlElement(name = "BatchFields", required = true)
			protected ImportSession.Batches.Batch.BatchFields batchFields;
			@XmlElement(name = "Documents", required = true)
			protected ImportSession.Batches.Batch.Documents documents;

			/**
			 * Gets the value of the batchFields property.
			 * 
			 * @return possible object is {@link ImportSession.Batches.Batch.BatchFields }
			 * 
			 */
			public ImportSession.Batches.Batch.BatchFields getBatchFields() {
				return batchFields;
			}

			/**
			 * Sets the value of the batchFields property.
			 * 
			 * @param value allowed object is
			 *              {@link ImportSession.Batches.Batch.BatchFields }
			 * 
			 */
			public void setBatchFields(ImportSession.Batches.Batch.BatchFields value) {
				this.batchFields = value;
			}

			/**
			 * Gets the value of the documents property.
			 * 
			 * @return possible object is {@link ImportSession.Batches.Batch.Documents }
			 * 
			 */
			public ImportSession.Batches.Batch.Documents getDocuments() {
				return documents;
			}

			/**
			 * Sets the value of the documents property.
			 * 
			 * @param value allowed object is {@link ImportSession.Batches.Batch.Documents }
			 * 
			 */
			public void setDocuments(ImportSession.Batches.Batch.Documents value) {
				this.documents = value;
			}

			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within
			 * this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="BatchField" maxOccurs="unbounded" minOccurs="0"&gt;
			 *           &lt;complexType&gt;
			 *             &lt;simpleContent&gt;
			 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
			 *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *                 &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *               &lt;/extension&gt;
			 *             &lt;/simpleContent&gt;
			 *           &lt;/complexType&gt;
			 *         &lt;/element&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "batchField" })
			public static class BatchFields {

				@XmlElement(name = "BatchField")
				protected List<ImportSession.Batches.Batch.BatchFields.BatchField> batchField;

				/**
				 * Gets the value of the batchField property.
				 * 
				 * <p>
				 * This accessor method returns a reference to the live list, not a snapshot.
				 * Therefore any modification you make to the returned list will be present
				 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
				 * for the batchField property.
				 * 
				 * <p>
				 * For example, to add a new item, do as follows:
				 * 
				 * <pre>
				 * getBatchField().add(newItem);
				 * </pre>
				 * 
				 * 
				 * <p>
				 * Objects of the following type(s) are allowed in the list
				 * {@link ImportSession.Batches.Batch.BatchFields.BatchField }
				 * 
				 * 
				 */
				public List<ImportSession.Batches.Batch.BatchFields.BatchField> getBatchField() {
					if (batchField == null) {
						batchField = new ArrayList<ImportSession.Batches.Batch.BatchFields.BatchField>();
					}
					return this.batchField;
				}

				/**
				 * <p>
				 * Java class for anonymous complex type.
				 * 
				 * <p>
				 * The following schema fragment specifies the expected content contained within
				 * this class.
				 * 
				 * <pre>
				 * &lt;complexType&gt;
				 *   &lt;simpleContent&gt;
				 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
				 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *     &lt;/extension&gt;
				 *   &lt;/simpleContent&gt;
				 * &lt;/complexType&gt;
				 * </pre>
				 * 
				 * 
				 */
				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "value" })
				public static class BatchField {

					@XmlValue
					protected String value;
					@XmlAttribute(name = "Name")
					protected String name;
					@XmlAttribute(name = "Value")
					protected String valueAttribute;

					/**
					 * Gets the value of the value property.
					 * 
					 * @return possible object is {@link String }
					 * 
					 */
					public String getValue() {
						return value;
					}

					/**
					 * Sets the value of the value property.
					 * 
					 * @param value allowed object is {@link String }
					 * 
					 */
					public void setValue(String value) {
						this.value = value;
					}

					/**
					 * Gets the value of the name property.
					 * 
					 * @return possible object is {@link String }
					 * 
					 */
					public String getName() {
						return name;
					}

					/**
					 * Sets the value of the name property.
					 * 
					 * @param value allowed object is {@link String }
					 * 
					 */
					public void setName(String value) {
						this.name = value;
					}

					/**
					 * Gets the value of the valueAttribute property.
					 * 
					 * @return possible object is {@link String }
					 * 
					 */
					public String getValueAttribute() {
						return valueAttribute;
					}

					/**
					 * Sets the value of the valueAttribute property.
					 * 
					 * @param value allowed object is {@link String }
					 * 
					 */
					public void setValueAttribute(String value) {
						this.valueAttribute = value;
					}

				}

			}

			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within
			 * this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Document" maxOccurs="unbounded" minOccurs="0"&gt;
			 *           &lt;complexType&gt;
			 *             &lt;complexContent&gt;
			 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *                 &lt;sequence&gt;
			 *                   &lt;element name="IndexFields"&gt;
			 *                     &lt;complexType&gt;
			 *                       &lt;complexContent&gt;
			 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *                           &lt;sequence&gt;
			 *                             &lt;element name="IndexField" maxOccurs="unbounded" minOccurs="0"&gt;
			 *                               &lt;complexType&gt;
			 *                                 &lt;simpleContent&gt;
			 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
			 *                                     &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *                                     &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *                                   &lt;/extension&gt;
			 *                                 &lt;/simpleContent&gt;
			 *                               &lt;/complexType&gt;
			 *                             &lt;/element&gt;
			 *                           &lt;/sequence&gt;
			 *                         &lt;/restriction&gt;
			 *                       &lt;/complexContent&gt;
			 *                     &lt;/complexType&gt;
			 *                   &lt;/element&gt;
			 *                   &lt;element name="Pages"&gt;
			 *                     &lt;complexType&gt;
			 *                       &lt;complexContent&gt;
			 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *                           &lt;sequence&gt;
			 *                             &lt;element name="Page"&gt;
			 *                               &lt;complexType&gt;
			 *                                 &lt;simpleContent&gt;
			 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
			 *                                     &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *                                   &lt;/extension&gt;
			 *                                 &lt;/simpleContent&gt;
			 *                               &lt;/complexType&gt;
			 *                             &lt;/element&gt;
			 *                           &lt;/sequence&gt;
			 *                         &lt;/restriction&gt;
			 *                       &lt;/complexContent&gt;
			 *                     &lt;/complexType&gt;
			 *                   &lt;/element&gt;
			 *                 &lt;/sequence&gt;
			 *                 &lt;attribute name="FormTypeName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
			 *               &lt;/restriction&gt;
			 *             &lt;/complexContent&gt;
			 *           &lt;/complexType&gt;
			 *         &lt;/element&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = { "document" })
			public static class Documents {

				@XmlElement(name = "Document")
				protected List<ImportSession.Batches.Batch.Documents.Document> document;

				/**
				 * Gets the value of the document property.
				 * 
				 * <p>
				 * This accessor method returns a reference to the live list, not a snapshot.
				 * Therefore any modification you make to the returned list will be present
				 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
				 * for the document property.
				 * 
				 * <p>
				 * For example, to add a new item, do as follows:
				 * 
				 * <pre>
				 * getDocument().add(newItem);
				 * </pre>
				 * 
				 * 
				 * <p>
				 * Objects of the following type(s) are allowed in the list
				 * {@link ImportSession.Batches.Batch.Documents.Document }
				 * 
				 * 
				 */
				public List<ImportSession.Batches.Batch.Documents.Document> getDocument() {
					if (document == null) {
						document = new ArrayList<ImportSession.Batches.Batch.Documents.Document>();
					}
					return this.document;
				}

				/**
				 * <p>
				 * Java class for anonymous complex type.
				 * 
				 * <p>
				 * The following schema fragment specifies the expected content contained within
				 * this class.
				 * 
				 * <pre>
				 * &lt;complexType&gt;
				 *   &lt;complexContent&gt;
				 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
				 *       &lt;sequence&gt;
				 *         &lt;element name="IndexFields"&gt;
				 *           &lt;complexType&gt;
				 *             &lt;complexContent&gt;
				 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
				 *                 &lt;sequence&gt;
				 *                   &lt;element name="IndexField" maxOccurs="unbounded" minOccurs="0"&gt;
				 *                     &lt;complexType&gt;
				 *                       &lt;simpleContent&gt;
				 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
				 *                           &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *                           &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *                         &lt;/extension&gt;
				 *                       &lt;/simpleContent&gt;
				 *                     &lt;/complexType&gt;
				 *                   &lt;/element&gt;
				 *                 &lt;/sequence&gt;
				 *               &lt;/restriction&gt;
				 *             &lt;/complexContent&gt;
				 *           &lt;/complexType&gt;
				 *         &lt;/element&gt;
				 *         &lt;element name="Pages"&gt;
				 *           &lt;complexType&gt;
				 *             &lt;complexContent&gt;
				 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
				 *                 &lt;sequence&gt;
				 *                   &lt;element name="Page"&gt;
				 *                     &lt;complexType&gt;
				 *                       &lt;simpleContent&gt;
				 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
				 *                           &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *                         &lt;/extension&gt;
				 *                       &lt;/simpleContent&gt;
				 *                     &lt;/complexType&gt;
				 *                   &lt;/element&gt;
				 *                 &lt;/sequence&gt;
				 *               &lt;/restriction&gt;
				 *             &lt;/complexContent&gt;
				 *           &lt;/complexType&gt;
				 *         &lt;/element&gt;
				 *       &lt;/sequence&gt;
				 *       &lt;attribute name="FormTypeName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
				 *     &lt;/restriction&gt;
				 *   &lt;/complexContent&gt;
				 * &lt;/complexType&gt;
				 * </pre>
				 * 
				 * 
				 */
				@XmlAccessorType(XmlAccessType.FIELD)
				@XmlType(name = "", propOrder = { "indexFields", "pages" })
				public static class Document {

					@XmlElement(name = "IndexFields", required = true)
					protected ImportSession.Batches.Batch.Documents.Document.IndexFields indexFields;
					@XmlElement(name = "Pages", required = true)
					protected ImportSession.Batches.Batch.Documents.Document.Pages pages;
					@XmlAttribute(name = "FormTypeName")
					protected String formTypeName;

					/**
					 * Gets the value of the indexFields property.
					 * 
					 * @return possible object is
					 *         {@link ImportSession.Batches.Batch.Documents.Document.IndexFields }
					 * 
					 */
					public ImportSession.Batches.Batch.Documents.Document.IndexFields getIndexFields() {
						return indexFields;
					}

					/**
					 * Sets the value of the indexFields property.
					 * 
					 * @param value allowed object is
					 *              {@link ImportSession.Batches.Batch.Documents.Document.IndexFields }
					 * 
					 */
					public void setIndexFields(ImportSession.Batches.Batch.Documents.Document.IndexFields value) {
						this.indexFields = value;
					}

					/**
					 * Gets the value of the pages property.
					 * 
					 * @return possible object is
					 *         {@link ImportSession.Batches.Batch.Documents.Document.Pages }
					 * 
					 */
					public ImportSession.Batches.Batch.Documents.Document.Pages getPages() {
						return pages;
					}

					/**
					 * Sets the value of the pages property.
					 * 
					 * @param value allowed object is
					 *              {@link ImportSession.Batches.Batch.Documents.Document.Pages }
					 * 
					 */
					public void setPages(ImportSession.Batches.Batch.Documents.Document.Pages value) {
						this.pages = value;
					}

					/**
					 * Gets the value of the formTypeName property.
					 * 
					 * @return possible object is {@link String }
					 * 
					 */
					public String getFormTypeName() {
						return formTypeName;
					}

					/**
					 * Sets the value of the formTypeName property.
					 * 
					 * @param value allowed object is {@link String }
					 * 
					 */
					public void setFormTypeName(String value) {
						this.formTypeName = value;
					}

					/**
					 * <p>
					 * Java class for anonymous complex type.
					 * 
					 * <p>
					 * The following schema fragment specifies the expected content contained within
					 * this class.
					 * 
					 * <pre>
					 * &lt;complexType&gt;
					 *   &lt;complexContent&gt;
					 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
					 *       &lt;sequence&gt;
					 *         &lt;element name="IndexField" maxOccurs="unbounded" minOccurs="0"&gt;
					 *           &lt;complexType&gt;
					 *             &lt;simpleContent&gt;
					 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
					 *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
					 *                 &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
					 *               &lt;/extension&gt;
					 *             &lt;/simpleContent&gt;
					 *           &lt;/complexType&gt;
					 *         &lt;/element&gt;
					 *       &lt;/sequence&gt;
					 *     &lt;/restriction&gt;
					 *   &lt;/complexContent&gt;
					 * &lt;/complexType&gt;
					 * </pre>
					 * 
					 * 
					 */
					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "indexField" })
					public static class IndexFields {

						@XmlElement(name = "IndexField")
						protected List<ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField> indexField;

						/**
						 * Gets the value of the indexField property.
						 * 
						 * <p>
						 * This accessor method returns a reference to the live list, not a snapshot.
						 * Therefore any modification you make to the returned list will be present
						 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
						 * for the indexField property.
						 * 
						 * <p>
						 * For example, to add a new item, do as follows:
						 * 
						 * <pre>
						 * getIndexField().add(newItem);
						 * </pre>
						 * 
						 * 
						 * <p>
						 * Objects of the following type(s) are allowed in the list
						 * {@link ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField }
						 * 
						 * 
						 */
						public List<ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField> getIndexField() {
							if (indexField == null) {
								indexField = new ArrayList<ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField>();
							}
							return this.indexField;
						}

						/**
						 * <p>
						 * Java class for anonymous complex type.
						 * 
						 * <p>
						 * The following schema fragment specifies the expected content contained within
						 * this class.
						 * 
						 * <pre>
						 * &lt;complexType&gt;
						 *   &lt;simpleContent&gt;
						 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
						 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
						 *       &lt;attribute name="Value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
						 *     &lt;/extension&gt;
						 *   &lt;/simpleContent&gt;
						 * &lt;/complexType&gt;
						 * </pre>
						 * 
						 * 
						 */
						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "value" })
						public static class IndexField {

							@XmlValue
							protected String value;
							@XmlAttribute(name = "Name")
							protected String name;
							@XmlAttribute(name = "Value")
							protected String valueAttribute;

							/**
							 * Gets the value of the value property.
							 * 
							 * @return possible object is {@link String }
							 * 
							 */
							public String getValue() {
								return value;
							}

							/**
							 * Sets the value of the value property.
							 * 
							 * @param value allowed object is {@link String }
							 * 
							 */
							public void setValue(String value) {
								this.value = value;
							}

							/**
							 * Gets the value of the name property.
							 * 
							 * @return possible object is {@link String }
							 * 
							 */
							public String getName() {
								return name;
							}

							/**
							 * Sets the value of the name property.
							 * 
							 * @param value allowed object is {@link String }
							 * 
							 */
							public void setName(String value) {
								this.name = value;
							}

							/**
							 * Gets the value of the valueAttribute property.
							 * 
							 * @return possible object is {@link String }
							 * 
							 */
							public String getValueAttribute() {
								return valueAttribute;
							}

							/**
							 * Sets the value of the valueAttribute property.
							 * 
							 * @param value allowed object is {@link String }
							 * 
							 */
							public void setValueAttribute(String value) {
								this.valueAttribute = value;
							}

						}

					}

					/**
					 * <p>
					 * Java class for anonymous complex type.
					 * 
					 * <p>
					 * The following schema fragment specifies the expected content contained within
					 * this class.
					 * 
					 * <pre>
					 * &lt;complexType&gt;
					 *   &lt;complexContent&gt;
					 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
					 *       &lt;sequence&gt;
					 *         &lt;element name="Page"&gt;
					 *           &lt;complexType&gt;
					 *             &lt;simpleContent&gt;
					 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
					 *                 &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
					 *               &lt;/extension&gt;
					 *             &lt;/simpleContent&gt;
					 *           &lt;/complexType&gt;
					 *         &lt;/element&gt;
					 *       &lt;/sequence&gt;
					 *     &lt;/restriction&gt;
					 *   &lt;/complexContent&gt;
					 * &lt;/complexType&gt;
					 * </pre>
					 * 
					 * 
					 */
					@XmlAccessorType(XmlAccessType.FIELD)
					@XmlType(name = "", propOrder = { "page" })
					public static class Pages {

						@XmlElement(name = "Page", required = true)
						protected ImportSession.Batches.Batch.Documents.Document.Pages.Page page;

						/**
						 * Gets the value of the page property.
						 * 
						 * @return possible object is
						 *         {@link ImportSession.Batches.Batch.Documents.Document.Pages.Page }
						 * 
						 */
						public ImportSession.Batches.Batch.Documents.Document.Pages.Page getPage() {
							return page;
						}

						/**
						 * Sets the value of the page property.
						 * 
						 * @param value allowed object is
						 *              {@link ImportSession.Batches.Batch.Documents.Document.Pages.Page }
						 * 
						 */
						public void setPage(ImportSession.Batches.Batch.Documents.Document.Pages.Page value) {
							this.page = value;
						}

						/**
						 * <p>
						 * Java class for anonymous complex type.
						 * 
						 * <p>
						 * The following schema fragment specifies the expected content contained within
						 * this class.
						 * 
						 * <pre>
						 * &lt;complexType&gt;
						 *   &lt;simpleContent&gt;
						 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
						 *       &lt;attribute name="ImportFileName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
						 *     &lt;/extension&gt;
						 *   &lt;/simpleContent&gt;
						 * &lt;/complexType&gt;
						 * </pre>
						 * 
						 * 
						 */
						@XmlAccessorType(XmlAccessType.FIELD)
						@XmlType(name = "", propOrder = { "value" })
						public static class Page {

							@XmlValue
							protected String value;
							@XmlAttribute(name = "ImportFileName")
							protected String importFileName;

							/**
							 * Gets the value of the value property.
							 * 
							 * @return possible object is {@link String }
							 * 
							 */
							public String getValue() {
								return value;
							}

							/**
							 * Sets the value of the value property.
							 * 
							 * @param value allowed object is {@link String }
							 * 
							 */
							public void setValue(String value) {
								this.value = value;
							}

							/**
							 * Gets the value of the importFileName property.
							 * 
							 * @return possible object is {@link String }
							 * 
							 */
							public String getImportFileName() {
								return importFileName;
							}

							/**
							 * Sets the value of the importFileName property.
							 * 
							 * @param value allowed object is {@link String }
							 * 
							 */
							public void setImportFileName(String value) {
								this.importFileName = value;
							}

						}

					}

				}

			}

		}

	}

}
