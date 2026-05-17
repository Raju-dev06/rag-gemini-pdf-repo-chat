RAG Gemini PDF Chat (Spring Boot + Gemini + PostgreSQL)

An AI-powered PDF Question Answering System built using Retrieval-Augmented Generation (RAG).

Users can upload a PDF document and ask questions about its content.
The system retrieves relevant document chunks using vector similarity search and generates answers using Google Gemini AI.

⸻

Demo

Upload a PDF and ask questions about the document.

Example:

Question
What technologies does Manoj know?

Answer
Java, Spring Boot, SQL, Microservices, AWS, Docker, and more extracted directly from the uploaded document.

⸻

Tech Stack

Backend
	•	Java 17
	•	Spring Boot
	•	REST APIs

AI
	•	Google Gemini API
	•	Embeddings + LLM

Database
	•	PostgreSQL
	•	pgvector (Vector Similarity Search)

Libraries
	•	Apache PDFBox (PDF processing)

Tools
	•	Maven
	•	IntelliJ IDEA
	•	Git
	•	GitHub

⸻

Features
	•	Upload PDF documents
	•	Extract text using Apache PDFBox
	•	Split documents into chunks
	•	Generate embeddings using Gemini
	•	Store embeddings in PostgreSQL vector database
	•	Perform vector similarity search
	•	Ask questions about uploaded documents
	•	Generate AI answers using Gemini
	•	Session-based document isolation

⸻

Architecture

PDF Upload
↓
Text Extraction (PDFBox)
↓
Text Chunking
↓
Embedding Generation (Gemini)
↓
Vector Storage (PostgreSQL + pgvector)
↓
Similarity Search
↓
Relevant Context Retrieval
↓
Gemini LLM
↓
Final Answer

⸻

API Endpoints

Upload PDF

POST /pdf/upload

Example Response

{
“message”: “PDF uploaded successfully”,
“sessionId”: “123abc”
}

⸻

Ask Question

GET /chat

Example

/chat?sessionId=123abc&question=What technologies are mentioned in the document?

⸻

How to Run Locally

Clone the repository

git clone https://github.com/manoj-thangaraj/rag-gemini-pdf-chat.git

Navigate into project

cd rag-gemini-pdf-chat

Add environment variables

Create a .env file or set environment variables

GEMINI_API_KEY=your_gemini_api_key
DB_URL=your_postgres_url
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

Run the application

mvn spring-boot:run

Open in browser

http://localhost:8080

⸻

Future Improvements
	•	User authentication
	•	Multi-document support
	•	Chat history
	•	Streaming AI responses
	•	Better UI/UX
	•	Cloud deployment

⸻

Author

Manoj Thangaraj

Backend Developer | Java | Spring Boot | AI Integration

GitHub
https://github.com/manoj-thangaraj
