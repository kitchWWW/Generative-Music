#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c''16 ais'16 gis'16 e'16 d'8 c'8 ais4 c'4 ais4 c'8 cis'8 dis'4 e'8 fis'8 gis'4 a'8 f'8 e'4 d'8 c'8 b4 c'4 b8 cis'8 d'4 c'4 \bar"|."
}

>>
\midi{}
\layout{}
}
