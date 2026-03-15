#!/usr/bin/env python3
import argparse
from io import BytesIO

import qrcode
from reportlab.lib.pagesizes import A4
from reportlab.lib.units import mm
from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader


def make_qr_png(url: str) -> ImageReader:
    qr = qrcode.QRCode(
        version=None,
        error_correction=qrcode.constants.ERROR_CORRECT_M,
        box_size=2,
        border=1,
    )
    qr.add_data(url)
    qr.make(fit=True)
    img = qr.make_image(fill_color="black", back_color="white")

    bio = BytesIO()
    img.save(bio, format="PNG")
    bio.seek(0)
    return ImageReader(bio)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--base-url", default="http://mint.home/inv", help="e.g. http://mint.home/inv")
    ap.add_argument("--start", type=int, default=1)
    ap.add_argument("--count", type=int, default=100)
    ap.add_argument("--out", default="labels.pdf")
    ap.add_argument("--label-w-mm", type=float, default=13.0)
    ap.add_argument("--label-h-mm", type=float, default=16.0)
    ap.add_argument("--qr-mm", type=float, default=10.0)
    ap.add_argument("--margin-mm", type=float, default=0.5)
    args = ap.parse_args()

    page_w, page_h = A4
    label_w = args.label_w_mm * mm
    label_h = args.label_h_mm * mm
    qr_size = args.qr_mm * mm
    margin = args.margin_mm * mm

    usable_w = page_w - 2 * margin
    usable_h = page_h - 2 * margin

    cols = int(usable_w // label_w)
    rows = int(usable_h // label_h)
    per_page = cols * rows

    c = canvas.Canvas(args.out, pagesize=A4)
    c.setTitle("QR Labels")

    font_id = 7
    pad = 1.2 * mm

    for idx in range(args.count):
        number = args.start + idx
        item_id = f"ID{number:06d}"
        url = args.base_url.rstrip("/") + "/" + item_id

        pos = idx % per_page
        if pos == 0 and idx > 0:
            c.showPage()

        r = pos // cols
        col = pos % cols

        x0 = margin + col * label_w
        y_top = page_h - margin - r * label_h
        y0 = y_top - label_h

        # Schnittkante
        c.rect(x0, y0, label_w, label_h, stroke=1, fill=0)

        # QR
        qr_img = make_qr_png(url)
        qr_x = x0 + pad
        qr_y = y_top - pad - qr_size
        c.drawImage(qr_img, qr_x, qr_y, width=qr_size, height=qr_size,
                    preserveAspectRatio=True, mask="auto")

        # Nur ID als Klartext
        c.setFont("Helvetica-Bold", font_id)
        text_y = qr_y - (0.3*(font_id+3)) * mm
        c.drawCentredString(
            x0 + label_w / 2,
            text_y,
            item_id
        )

    c.save()
    print(f"Written: {args.out}")
    print(f"A4 grid: {cols} × {rows} = {per_page} labels/page")


if __name__ == "__main__":
    main()
