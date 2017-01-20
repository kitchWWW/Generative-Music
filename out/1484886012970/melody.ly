#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c''16 b'16 a'8 b'4 a'4 g'4 f'8. d'16 c'8 b8 a4 g4 a4 b4 a8 c'8 d'16 e'8 f'16 g'4 f'4 g'4 b'4 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
