
// MFCGLProjectDlg.cpp: Implementierungsdatei
//

#include "pch.h"
#include "framework.h"
#include "MFCGLProject.h"
#include "MFCGLProjectDlg.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CAboutDlg-Dialogfeld für Anwendungsbefehl "Info"

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// Dialogfelddaten
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ABOUTBOX };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV-Unterstützung

// Implementierung
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CMFCGLProjectDlg-Dialogfeld



CMFCGLProjectDlg::CMFCGLProjectDlg(CWnd* pParent /*=nullptr*/)
	: CDialogEx(IDD_MFCGLPROJECT_DIALOG, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMFCGLProjectDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_STATIC_GLSTATUS, mStaticGLStatus);
	DDX_Control(pDX, IDC_BUTTON_GLINIT, mButtonGLInit);
	DDX_Control(pDX, IDC_BUTTON_GLTOGGLE, mButtonGLToggle);
	DDX_Control(pDX, IDC_BUTTON_GLREFRESH, mButtonGLRefresh);
}

BEGIN_MESSAGE_MAP(CMFCGLProjectDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_BUTTON_GLINIT, &CMFCGLProjectDlg::OnBnClickedButtonGlinit)
	ON_BN_CLICKED(IDC_BUTTON_GLTOGGLE, &CMFCGLProjectDlg::OnBnClickedButtonGltoggle)
	ON_BN_CLICKED(IDC_BUTTON_GLREFRESH, &CMFCGLProjectDlg::OnBnClickedButtonGlrefresh)
END_MESSAGE_MAP()


// CMFCGLProjectDlg-Meldungshandler

BOOL CMFCGLProjectDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// Hinzufügen des Menübefehls "Info..." zum Systemmenü.

	// IDM_ABOUTBOX muss sich im Bereich der Systembefehle befinden.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != nullptr)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Symbol für dieses Dialogfeld festlegen.  Wird automatisch erledigt
	//  wenn das Hauptfenster der Anwendung kein Dialogfeld ist
	SetIcon(m_hIcon, TRUE);			// Großes Symbol verwenden
	SetIcon(m_hIcon, FALSE);		// Kleines Symbol verwenden

	// TODO: Hier zusätzliche Initialisierung einfügen

	return TRUE;  // TRUE zurückgeben, wenn der Fokus nicht auf ein Steuerelement gesetzt wird
}

void CMFCGLProjectDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// Wenn Sie dem Dialogfeld eine Schaltfläche "Minimieren" hinzufügen, benötigen Sie
//  den nachstehenden Code, um das Symbol zu zeichnen.  Für MFC-Anwendungen, die das 
//  Dokument/Ansicht-Modell verwenden, wird dies automatisch ausgeführt.

void CMFCGLProjectDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // Gerätekontext zum Zeichnen

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Symbol in Clientrechteck zentrieren
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Symbol zeichnen
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

// Die System ruft diese Funktion auf, um den Cursor abzufragen, der angezeigt wird, während der Benutzer
//  das minimierte Fenster mit der Maus zieht.
HCURSOR CMFCGLProjectDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


void CMFCGLProjectDlg::OnBnClickedButtonGlinit()
{
	// TODO: Fügen Sie hier Ihren Handlercode für Benachrichtigungen des Steuerelements ein.	
	if (TRUE) {
		mButtonGLInit.EnableWindow(FALSE);
		mButtonGLToggle.EnableWindow(TRUE);
		mStaticGLStatus.SetWindowTextW(_T("Erfolg\nOpen GL ist initialisiert."));
	}
	else {
		mStaticGLStatus.SetWindowTextW(_T("Fehler\nOpen GL konnte nicht initialisiert werden."));
	}
	UpdateData(FALSE);
}

bool visible = false;
void CMFCGLProjectDlg::OnBnClickedButtonGltoggle()
{
	// TODO: Fügen Sie hier Ihren Handlercode für Benachrichtigungen des Steuerelements ein.	
	visible = !visible;
	if (visible) {
		mButtonGLRefresh.EnableWindow(TRUE);
		mStaticGLStatus.SetWindowTextW(_T("Erfolg\nOpen GL Fenster ist sichtbar."));
	}
	else {
		mButtonGLRefresh.EnableWindow(FALSE);
		mStaticGLStatus.SetWindowTextW(_T("Erfolg\nOpen GL Fenster ist unsichtbar."));
	}
}

void CMFCGLProjectDlg::OnBnClickedButtonGlrefresh()
{
	// TODO: Fügen Sie hier Ihren Handlercode für Benachrichtigungen des Steuerelements ein.
}
