#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'16 b8 a16 b8 c'8 d'4 e'8 d'8 c'4 b16 a16 b8 c'4 b8 c'8 d'4 d'4 e'4 d'4 c'4 b4 a4 f'16 g'8 b'16 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
