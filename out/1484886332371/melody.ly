#(set-global-staff-size 25)
\paper {indent = 0\cm}
\header{tagline = ""}

\score{
 << 

\absolute {
\override Score.BarNumber.break-visibility = ##(#f #f #f) c'16 d'16 f'16 e'16 g'16 a'16 c''16 d''16 e''8 d''8 c''16 a'16 g'16 b16 a4 f'16 g'8. a'8 b'8 c''8 d''8 d''8 c''16 e'16 d'8 c'16 a16 a4 f'16 g'8 a'16 b'8 a'8 g'4 f'4 g'8 b'8 c''4 \bar"|."
}

>>
\midi{}
\layout{}
}
