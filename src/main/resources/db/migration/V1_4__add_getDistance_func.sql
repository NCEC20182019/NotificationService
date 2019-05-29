create or replace function getDistance(lat1 float,
                                       lng1 float,
                                       lat2 float,
                                       lng2 float) returns numeric as
$$
declare
  x          real := 0;
  y          real := 0;
  R constant int  := 6371 * 1000;
begin
  x := (radians(lng2) - radians(lng1)) * cos((radians(lat1) + radians(lat2)) / 2);
  y := radians(lat2) - radians(lat1);
  return round((sqrt(x * x + y * y) * R)::numeric, 2);
end;
$$ language plpgsql;