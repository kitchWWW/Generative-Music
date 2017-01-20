#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c''16 b'8 a'16 g'8 a'8 b'4 c''8 b'8 a'4 f'8 e'8 c'8 b8 a8. c'16 d'4 e'4 d'4 e'4 f'8 e'8 d'4 f'16 g'16 b'16 c''16 d''4 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
