#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) g16 a8 b16 c'8 e'8 f'4 e'8 d'8 c'4 b4 a4 c'8 b8 a8 b8 a8. e'16 d'16 f'8 g'16 a'4 g'4 a'4 b'8 a'8 g'16 e'16 d'8 c'4 \bar"|."
}

>>
\midi{}
\layout{}
}
